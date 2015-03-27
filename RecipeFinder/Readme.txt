Main class file - com.recipeFinder.RecipeRecommender

Running the program - 
Please run RecipeFinder.bat to execute the program.

Input -
The RecipeRecommender class takes command line input. 
Absolute file path with filename is expected. e.g. C:\Work\pactera\input\fridge.csv
Since multi line input is expected, please enter a blank line to submit response.

Test - 
Individual classes have been tested with Junit (Included in the source)
The program has been tested with input/fridge.csv and the following JSON object.
[
{
"name": "grilled cheese on
toast", "ingredients": [
{ "item":"bread", "amount":"2", "unit":"slices"},
{ "item":"cheese", "amount":"2", "unit":"slices"}
]
}
,
{
"name": "salad sandwich",
"ingredients": [
{ "item":"bread", "amount":"2", "unit":"slices"},
{ "item":"mixed salad", "amount":"100", "unit":"grams"}
]
}
]