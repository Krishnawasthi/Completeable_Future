package com.thread.completablefuture.SupplyAsync;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SupplyAsync {

	 // SupplyAsync used when you just want to print you  need any return type
		public static void main(String[] args) throws InterruptedException, ExecutionException {
		
			System.out.println("SupplyAsync.main() START");
			CompletableFuture completableFuture 	 = CompletableFuture.supplyAsync(() -> {
				System.out.println("I am Supply async");
				return "SUCCESS";
			});
			//when you want anything in return then use SupplyAsync
			System.out.println("SupplyAsync return something: "+ completableFuture.join());
			System.out.println("SupplyAsync.main() END");    
		}
}
