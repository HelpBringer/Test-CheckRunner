Small test project imitating work with products. 

feature/entry-core is basic branch with default file location.
Example request: java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 1-1 2-1 2-2 3-2 3-3 1-2 discountCard=1111 balanceDebitCard=1000

feature/entry-file is branch with requested location for result and product files.
Example request: java -cp src ./src/main/java/ru/clevertec/check/CheckRunner.java 1-1 2-1 2-2 3-2 3-3 1-2 discountCard=1111 balanceDebitCard=1000 pathToFile=./src/main/resources/products.csv saveToFile=./test_result.csv

Where set of numbers is id-quantity for required products, discountCard - id of discount card, balanceDebitCard - balance of the desired card, pathToFile - path to data of all products, saveToFile - path to result file.

product.csv format:

id;description;price;quantity_in_stock;wholesale_product
1;Milk;1.07;10;true
2;Cream 400g;2.71;20;true
3;Yogurt 400g;2.10;7;true

discountCards.csv format:

id;number;discount_amount
1;1111;3
2;2222;3

10.07.2024
