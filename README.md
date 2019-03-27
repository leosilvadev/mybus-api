# My bus API

Open Api that provides fictional information about public transportation.
All the data about stops, times and delay are provided by csv file inside *src/main/resources*.


# Available endpoints

#### Search for the closest stop from the given x/y point

*The query parameter "time" can be omitted, so the time considered will be the current time.*

`GET /api/v1/coordinates/:x/:y/stops/next?time=10:01:05`


#### Search for the next vehicle arriving at the given x/y stop. 
It is important to note that the x/y points must match an existent Stop.

*The query parameter "time" can be omitted, so the time considered will be the current time.*

`GET /api/v1/coordinates/:x/:y/vehicles/next`


#### Search for a delay in the given line name

`GET /api/v1/lines/:lineName/delays`



