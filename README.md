# urlshortener
## NOTE
### This project was built under windows and presumes that docker has been given file sharing permission on the C: drive. If you see DB creation errors, please ensure docker has been given the correct permissions for the C drive. This can be done through Docker Desktop.


## index.html
### There is an index.html file in the root of the project. Once the container is running, you can open this to see an input field for your url.


## Building the Project
### In the project root, execute ./docker-prepare.sh

## Running the Container
### In the project root, execute ./run-project.sh

## API Information
### POST Endpoint
URL: http://localhost:8090/createurl

Return Value: Shortened URL

The POST endpoint accepts a request with a JSON body that looks like the following:

{
    "url": "http://www.rte.ie/news"
}

### GET Endpoint With Generated String
URL: http://localhost:8090/"generated-string"

Return Value: Full Original URL and a Redirect

The GET endpoint accepts the URL generated earlier a URL submission

  
### GET Endpoint With Query Params
URL: http://localhost:8090/?url=<url\>

Return Value: Shortened URL

The GET endpoint with query params returns a shortened url
