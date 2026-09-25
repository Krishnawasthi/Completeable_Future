package com.thread.completablefuture.SupplyAsync;

import java.util.concurrent.CompletableFuture;

public class ThenApply {

	public static void main(String[] args) {
		CompletableFuture<String> cf = 	CompletableFuture.supplyAsync(() -> {
		   String name = "krishna";
		return  name; 
		}).thenApply(n -> n.toUpperCase());
       
      System.out.println("Response: "+ cf.join()+ " " +Thread.currentThread().getName());

    }
}