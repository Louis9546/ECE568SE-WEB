Steps:
1: Create a java project by yourself
2: Load all my files under the file:"Project" into your projcet
3: Load the Jama-1.0.3 into your library
3: Run the "StockPrediction.java"
4: Choose the stock you wanna test (Range 1 -5) and input the number of data, whose range is 1 - 250.
5: I also calculate the variance of the Bayesian curve fitting at BCF.java and implement it at line 45 in StockPrediction.java. If you wanna check the variance of the model, just uncomment the 45 line and 59 line.


My Data (CVS file):
1: I download five stocks' information from Yahoo Finance and each stock has 251 prices. 
The title from left to right should be Date, Open, High, Low, Close, Adj Close, and Volume (Shown in my Performance Evaluation file). 

2: The date of stock is descending, namely, the first row is the latest one. So in my coding, after I read the line of Excel, I assign the value to prices array from down to up. 

3: All the stocks data are saved in the file called "StockPrice"