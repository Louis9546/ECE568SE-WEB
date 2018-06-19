package com.company;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StockPrediction {
//	public static void main(String[] args) {
//		Reader reader;
//		BCF fit;
//		String cont = "Y";
//		int num = 0;
//		double absMeanErr = 0.0;    // absolute mean error
//		double relErr = 0.0;    // relative error;
//		double Err = 0.0;
//		double sumErr = 0.0;
//		double sumPrice = 0.0;
//		while (cont.equals("Y") || cont.equals("y")) {
////			String[] stockList = {"CSV", "GOGO", "FB", "ARCO", "AABA","AMZN_test"};
//			String[] stockList = {"Data1", "Data2","Data3","Data4","Data5"};
//			System.out.println("Please choose a stock symbol:");
//			for (int i = 0; i < stockList.length; i++) {
//				System.out.println((i + 1) + ". " + stockList[i]);
//			}
//			Scanner sc = new Scanner(System.in);
//			int stock = sc.nextInt();
//			while (stock <= 0 || stock > stockList.length) {
//				System.out.println("Please input again.");
//				stock = sc.nextInt();
//			}
//			System.out.println("Please input the number of data:");
//			int N = sc.nextInt();
//			while (N <= 0 || N > 250) {
//				System.out.println("Please input again (Max: 250)");
//				N = sc.nextInt();
//			}
//			double[] x = new double[5 * N];
//			for (int i = 0; i < 5 * N; i++) {
//				x[i] = i + 1;
//			}
//			reader = new Reader(N, stockList[stock - 1]);
//
//			double[] stockPrices = reader.getPrices();
//			int m = 9;    // order of polynomial
//			fit = new BCF(x, stockPrices, m);
//			double prediction = fit.getMx(N + 1);
////			double s2x = fit.getS2X(N + 1); //The variance of the the model
//			double actualPrice = reader.getActualPrice();
//			num++;
//			Err = Math.abs(actualPrice - prediction);
//			sumErr += Err;
//			sumPrice += actualPrice;
//			absMeanErr = sumErr / num;
//			relErr = sumErr / sumPrice;
//			System.out.println("Stock: " + stockList[stock - 1]);
//			System.out.printf("Predicted price: %.5f\n", prediction);
//			System.out.printf("Actual price is: %.5f\n", actualPrice);
//			System.out.printf("Absolute error: %.2f\n", Err);
//			System.out.printf("precision: %.5f\n", Err/actualPrice);
//			System.out.printf("Absolute mean error: %.2f\n", absMeanErr);
//			System.out.println("Average relative error: " + relErr);
//			System.out.println("Variance is: " + s2x);
//			System.out.println("Continue? (Y/N)");
//			cont = sc.next();
//		}
//	}
}


class Reader {
	private int N;// number of data
	private double[] prices;// the final price of stocks
	private String[] allPricess;//temporary array used to save stocks price
	int times;

	public boolean firstQuotation(String s){
		if(s.charAt(0) == '\"') return true;
		else return false;
	}

	public boolean lastQuotation(String s){
		if(s.charAt(s.length() - 1) == '\"') return true;
		else return false;
	}


	public Reader(int N, String symbol) {
		this.N = N;
		int si = N * 5;
		prices = new double[si];
		String file = "StockPrice/" + symbol + ".csv";
		allPricess = new String[300];
		String line = "";
		String stop = ",,,,,,";
		times = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			line = reader.readLine();
			line = reader.readLine();//if have title

			while(line != null && !line.equals(stop)) {
				String[] item = line.split(",");
				String[] finalItem = new String[10];
				ArrayList<String> trueItem = new ArrayList<>();
				StringBuffer temp = new StringBuffer();
				int flag = 0;
				for(String i : item){
					if (!i.contains("\"") && flag == 0){
						trueItem.add(i);
					}
					else if (!i.contains("\"") && flag == 1){
						temp.append(i);
					}
					else{
						if(firstQuotation(i)){
							temp.append(i.substring(1, i.length()));
						}
						else if(lastQuotation(i)){
							temp.append(i.substring(0, i.length() - 1));
						}
						flag = 1;
						if(lastQuotation(i)){
							flag = 0;
							trueItem.add(temp.toString());
							temp.delete(0, temp.length());
						}
					}
				}
				for(int i = 0; i < trueItem.size(); i++){
					finalItem[i] = trueItem.get(i);// each line of the excel
				}

				allPricess[times] = finalItem[4];//!! Important!! read the fifth column(close price)
				times++;
				line = reader.readLine();
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		for(int tim = 0; tim < 5; tim++){
			for(int i = 0; i < N; i++) {
				prices[tim * N + i] = Double.parseDouble(allPricess[times - i - 1]); //save the price into the array from down to up
			}
		}


	}

	public double[] getPrices() {
		return prices;
	}

	public double getActualPrice() {
		return Double.parseDouble(allPricess[times - N - 1]);
	}

}
