# quick url

Url shortener "kata"

API usage:

| Http method | endpoint | request body | description | response |
| --- | --- | --- | --- | --- |
| POST | /shorten | `{ "url": "https://google.com" }`               | generate a random slug                     |  201 `{"url": "https://google.com","slug": "opt-nk"}`|
| POST | /shorten | `{ "url": "https://google.com", "slug": "gg" }` | use a user-provided slug                   |  201 `{"url": "https://google.com","slug": "gg"}`|
| GET  | /{slug}  | -                                               | redirects to the given slug (if it exists) |  302, redirecting to the url reigstered to {slug} |

## Building the project

The project uses maven for dependency management and building:

If you have Java installed locally:
- `./mvnw clean install`

Otherwise, a maven docker image can be used directly:
- `docker run -it --rm -v "$PWD":/usr/src/quickurl -v "$HOME/.m2":/root/.m2 -w /usr/src/quickurl maven:3.3-jdk-8 mvn clean install`
    - (`-v "$HOME/.m2":/root/.m2` is optional, but allows for maven cache to be reused in subsequent runs, speeding up the build process a lot)

And then you can build an image and run the app using:

```
docker build -t test/quickurl . && \
docker run \
    --interactive \
    --tty \
    --rm \
    --network quickurl_network \
    --env SPRING_DATA_MONGODB_HOST=quickurl_mongo \
    -p 8080:8080 \
    --name test \
    test/quickurl \
    java -jar /opt/app/quickurl.jar
```
