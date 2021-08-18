# car-parking-main
###### Contains the following API's
##### basePath("/api/v1/)
API's
## /parkings (GET)
API for returning the number of available parking spots

## /parkings (POST)
API for reserving a parking spot.

Accepts JSON Body  
```
{
"carNumber" : "String"
"hours" : "String
}
```

## /parkings?extendingTimes=value (PUT)
API for extending the time.

Accepts JSON Body  
```
{
"carNumber" : "String"
"hours" : "String
}
```

## /parkings/{parkingId} (DELETE)
