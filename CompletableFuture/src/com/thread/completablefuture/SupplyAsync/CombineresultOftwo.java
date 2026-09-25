package com.thread.completablefuture.SupplyAsync;

import java.util.concurrent.CompletableFuture;

public class CombineresultOftwo {

	public static void main(String[] args) {
	CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
	CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 30);
	CompletableFuture<Integer> finalfuture = future1.thenCombine(future2 ,(n1, n2) -> n1+n2 );
	
	System.out.println(finalfuture.join());
	}

}
