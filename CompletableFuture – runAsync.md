# Completeable_Future
# CompletableFuture – runAsync

## What is CompletableFuture?

`CompletableFuture` is a Java class used to perform tasks **asynchronously** and handle their results or completion without blocking the main flow unnecessarily.

It is available in:

```java
java.util.concurrent.CompletableFuture
```

It is mainly useful when we have a task that may take some time and we don't want the main thread to perform that task directly.

---

## Why do we need CompletableFuture?

Consider a normal program:

```java
System.out.println("START");

doSomeLongTask();

System.out.println("END");
```

Here, the main thread has to wait for `doSomeLongTask()` to finish before it can execute `"END"`.

If the task takes 5 seconds, the main thread remains blocked for those 5 seconds.

This becomes a problem in applications where we have tasks such as:

* Calling an API
* Reading data from a database
* Processing a large amount of data
* Sending an email
* Performing background processing
* Calling multiple services

`CompletableFuture` allows us to execute such tasks asynchronously and provides a way to know when the task has completed.

---

# runAsync()

`runAsync()` is used when we want to perform an asynchronous task but **do not need a result back**.

Syntax:

```java
CompletableFuture.runAsync(() -> {
    // task
});
```

For example:

```java
CompletableFuture<Void> future =
        CompletableFuture.runAsync(() -> {
            System.out.println("Task is running");
        });
```

The important point is:

> `runAsync()` performs the task asynchronously and returns `CompletableFuture<Void>` because there is no result to return.

---

# Why `runAsync()`?

Suppose we want to send an email after a user registers.

We don't necessarily need the email-sending operation to return a value.

```text
User Registration
       |
       v
Save User
       |
       v
Send Email  ----> Background task
       |
       v
Continue application
```

This is a good use case for `runAsync()`.

The task is something we want to **execute**, but we don't need a calculated result from it.

---

# Basic Example

```java
System.out.println("START");

CompletableFuture<Void> future =
        CompletableFuture.runAsync(() -> {
            System.out.println("I am running asynchronously");
        });

future.join();

System.out.println("END");
```

Possible output:

```text
START
I am running asynchronously
END
```

The asynchronous task is submitted for execution, and `join()` is used when we want to wait until that task has completed.

---

# What does `get()` do?

```java
future.get();
```

`get()` waits for the `CompletableFuture` to complete.

If the asynchronous task has not finished yet, the current thread waits.

Once the task is completed, `get()` continues to the next statement.

For example:

```java
future.get();
System.out.println("END");
```

The `"END"` statement will execute only after the future has completed.

### Important point

`get()` throws checked exceptions:

```java
InterruptedException
ExecutionException
```

Therefore, we commonly need:

```java
try {
    future.get();
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}
```

---

# What does `join()` do?

`join()` also waits for the `CompletableFuture` to complete.

```java
future.join();
```

The major difference is exception handling.

`join()` does **not** require us to handle checked exceptions such as `InterruptedException` and `ExecutionException`.

If the asynchronous task fails, `join()` throws an unchecked:

```java
CompletionException
```

Because of this, `join()` is often more convenient when working with `CompletableFuture`.

---

# get() vs join()

| `get()`                         | `join()`                                      |
| ------------------------------- | --------------------------------------------- |
| Waits for completion            | Waits for completion                          |
| Returns the result if available | Returns the result if available               |
| Throws checked exceptions       | Throws unchecked `CompletionException`        |
| Requires exception handling     | No checked exception handling required        |
| Comes from `Future`             | Designed as part of `CompletableFuture` usage |

In your `runAsync()` example, both ultimately wait for the asynchronous operation to finish.

---

# Why does runAsync() return Void?

`runAsync()` is designed for tasks where we **don't need a result**.

For example:

```java
CompletableFuture.runAsync(() -> {
    System.out.println("Sending email...");
});
```

There is no value being calculated and returned.

Therefore:

```java
CompletableFuture<Void>
```

is used.

If we actually need a result, we use:

```java
supplyAsync()
```

---

# runAsync() vs supplyAsync()

### runAsync()

Use when:

> "Perform this task, but I don't need a result."

```java
CompletableFuture.runAsync(() -> {
    sendEmail();
});
```

### supplyAsync()

Use when:

> "Perform this task and give me a result."

```java
CompletableFuture<String> future =
        CompletableFuture.supplyAsync(() -> {
            return "Data";
        });
```

So the simple difference is:

```text
runAsync()    → performs task → no result
supplyAsync() → performs task → returns result
```

---

# Does CompletableFuture always mean the main thread will never wait?

No.

This is an important interview point.

Creating a `CompletableFuture` with `runAsync()` starts an asynchronous task, but if we immediately call:

```java
future.join();
```

the current thread will wait for that task to complete.

So the benefit is not simply:

> "CompletableFuture means no blocking."

Rather, it provides a framework for **asynchronous execution and composition of tasks**.

For example, instead of immediately waiting, we can attach another action:

```java
future.thenRun(() -> {
    System.out.println("Task completed");
});
```

Now we can define what should happen after the first task finishes.

---

# What Problem Does CompletableFuture Solve?

Traditional code often handles asynchronous work using threads manually:

```text
Create Thread
      ↓
Start Thread
      ↓
Perform Task
      ↓
Wait for Thread
      ↓
Handle Result
```

Managing this manually becomes complicated when multiple asynchronous operations are involved.

`CompletableFuture` provides a higher-level API for:

* Running tasks asynchronously
* Waiting for completion
* Getting results
* Chaining tasks
* Combining multiple tasks
* Handling exceptions
* Executing actions after completion

This makes asynchronous programming easier to structure.

---

# Important Methods

Some commonly used methods are:

```text
runAsync()
    ↓
Execute task without returning a result

supplyAsync()
    ↓
Execute task and return a result

thenApply()
    ↓
Transform a result

thenAccept()
    ↓
Consume a result

thenRun()
    ↓
Run an action after completion

exceptionally()
    ↓
Handle an exception

get()
    ↓
Wait and get result

join()
    ↓
Wait and get result
```

---

# Simple Mental Model

Think of `CompletableFuture` as a **promise of a future result or completion**.

```text
CompletableFuture
       |
       +---- Task is running
       |
       +---- Task completed
       |
       +---- Result available
       |
       +---- Exception occurred
```

The `Future` represents something that will be completed in the future.

`CompletableFuture` goes further because we can also tell Java:

> "When this task finishes, perform another task."

That is what makes it more powerful than a basic `Future`.

---

# Key Interview Points

### 1. What is CompletableFuture?

`CompletableFuture` is a Java class used for asynchronous and non-blocking-style task composition and completion handling.

### 2. What is runAsync()?

`runAsync()` executes a task asynchronously when no result is required.

### 3. What does runAsync() return?

It returns:

```java
CompletableFuture<Void>
```

because the task does not produce a result.

### 4. What does join() do?

It waits until the `CompletableFuture` completes and does not require checked exception handling.

### 5. What does get() do?

It waits until completion and can return the result, but it throws checked exceptions.

### 6. Difference between runAsync() and supplyAsync()?

```text
runAsync()    → no result
supplyAsync() → returns result
```

---

# Final Summary

`CompletableFuture` is useful when an application needs to perform tasks asynchronously and coordinate what happens after those tasks complete. `runAsync()` is used when we only need to execute a task and don't need a return value. `get()` and `join()` can both wait for the task to complete; `get()` uses checked exceptions, while `join()` uses unchecked exception handling. The real power of `CompletableFuture` comes from methods such as `thenApply()`, `thenAccept()`, `thenRun()`, and `exceptionally()`, which allow multiple asynchronous operations to be connected together without manually managing threads.
