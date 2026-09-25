package com.thread.completablefuture.runasync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class RunAsync {
    // runasync used when you just want to print you dont need any return type
	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
		System.out.println("RunAsync.main() START");
		CompletableFuture completableFuture 	 = CompletableFuture.runAsync(() -> {
			System.out.println("I am run async");
		});
		//when you dont want anything in return then use runAsync
		System.out.println(completableFuture.join());
		System.out.println("RunAsync.main() END");    
	}

}
