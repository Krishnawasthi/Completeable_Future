package com.thread.completablefuture.SupplyAsync;

import java.util.concurrent.CompletableFuture;

public class ThenAccept {

	public static void main(String[] args) {
		CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
		   String name = "krishna";
		return  name; 
		}).thenAccept(n -> System.out.println("I'm printing ThenAccept"));
       //Async is used that mutiple thread are running 
      System.out.println("Response: "+ cf.join()+ " " +Thread.currentThread().getName()); 
      //response will be becuase it  does not return anything

    }

}
