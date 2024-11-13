# Pollster
A Springboot based app for creating polls. Each poll has a question and a number of options. Options can be voted on.

## Adding polls
Send a json request using curl to the `poll/savePoll` route. A poll must have between 2 and 7 options or the request will fail.

```bash
curl -H "Content-Type: application/json" -X POST -d '{
  "question": "What is your favourite food?",
  "optionList": [{
                 "text": "Salad"
              },
              {
                 "text": "Pasta"
              },
              {
                 "text": "Chips"
              }]
}' http://localhost:8080/poll/savePoll
```

Each poll gets a unique id. You can see the poll that you created by sending a request to the `poll/getPoll/{id_of_poll}` route.

```bash
curl -X GET http://localhost:8080/poll/getPoll/8
```

Each option within a poll also gets a unique id and you can vote for it by sending a request to `poll/saveVote?id={option_id}`

```bash
curl -H "Content-Type: application/json" -X POST http://localhost:8080/poll/saveVote?id=8
```

## Storing data
The app has an `application.properties` file setup to use postgres. By default it uses the username/password of `postgres`. Which is also used by the docker compose file provided.

```bash
docker compose up -d db
```

## Running
Ensure that postgres is runnind and the `application.properties` file has the correct details.
```bash
mvn spring-boot:run
```

## Testing
```bash
mvn test -Dtest=PollControllerTest.java
```