function FletchingRecipe(Item1,Item2,EndProduct) {
    this.Item1 = Item1;
    this.Item2 = Item2;
    this.EndProduct = EndProduct;
}

function getRecipes() {
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.open("GET","recipes.xml",false);
    xmlhttp.send(null);
    xmlDoc=xmlhttp.responseXML;

    var Recipes = [];

    var Item1Array=xmlDoc.documentElement.getElementsByTagName("Item1");
    var Item2Array=xmlDoc.documentElement.getElementsByTagName("Item2");
    var EndProductArray=xmlDoc.documentElement.getElementsByTagName("EndProduct");
    for (i=0;i<Item1Array.length;i++)
    {
        var Item1 = Item1Array[i].childNodes[0].nodeValue;
        var Item2 = Item2Array[i].childNodes[0].nodeValue;
        var EndProduct = EndProductArray[i].childNodes[0].nodeValue;
        Recipes.push(new FletchingRecipe(Item1, Item2, EndProduct));
    }
    return Recipes;
}

function FletchingItem(InGameName,ID,ImageURL) {
    this.InGameName = InGameName;
    this.ID = ID;
    this.ImageURL = ImageURL;
}

function getItems() {
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.open("GET","items.xml",false);
    xmlhttp.send(null);
    xmlDoc=xmlhttp.responseXML;

    var Items = [];

    var InGameName=xmlDoc.documentElement.getElementsByTagName("InGameName");
    var ID=xmlDoc.documentElement.getElementsByTagName("ID");
    var ImageURL=xmlDoc.documentElement.getElementsByTagName("ImageURL");
    for (i=0;i<InGameName.length;i++)
    {
        var IGN = InGameName[i].childNodes[0].nodeValue;
        var id = ID[i].childNodes[0].nodeValue;
        var Imageurl = ImageURL[i].childNodes[0].nodeValue;
        Items.push(new FletchingItem(IGN, id, Imageurl));
    }
    return Items;
}
function getEndProductArray() {
    var AllRecipes = getRecipes();
    var ReturnArray = [];
    for (i=0;i<AllRecipes.length;i++)
    {
        ReturnArray.push(AllRecipes[i]['EndProduct']);
    }
    return ReturnArray;
}
function getRecipeFromEndProduct(EndProduct) {
    var AllRecipes = getRecipes();
    for (i=0;i<AllRecipes.length;i++)
    {
        if(AllRecipes[i]['EndProduct']==EndProduct){
            return AllRecipes[i];
        }
    }
}
function getItemURLByName(Name) {
    var AllItems = getItems();
    for (i=0;i<AllItems.length;i++)
    {
        if(AllItems[i]['InGameName']==Name){
            return AllItems[i]['ImageURL'];
        }
    }
}