package com.thread.completablefuture.SupplyAsync;

import java.util.concurrent.CompletableFuture;

public class CombineresultOftwo {

	public static void main(String[] args) {
	CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
	CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 30);
	CompletableFuture<Integer> finalfuture = future1.thenCombine(future2 ,(n1, n2) -> n1+n2 );
	
	System.out.println(finalfuture.join());
	
	CompletableFuture.allOf(future1 , future2); //both will be executed
	CompletableFuture.anyOf(future1 , future2); //one of them get executed
	
	System.out.println("task has been done.....");
	}

}
