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

You can get a list of all polls by sending a rquest to `polls/getPolls`.
```bash
curl -X GET http://localhost:8080/poll/getPolls
```
```json
[{"id":1,"question": "What is your most favouritest food?"},{"id":2,"question": "What is your favourite season?"}]
```

Get the details for a single option plus the current vote count.
```bash
curl -X GET http://localhost:8080/poll/getOption/1
```
```json
{"id":1, "text":"Beans", "votes":5}
```

Get all the votes for a particular poll including the option voted for and the time.
```bash
curl -X GET http://localhost:8080/poll/getVotesForPoll/1
```
```json
[{"option":1,"created_at": "2024-11-13 13:52:01.742"},{"option":1,"created_at": "2024-11-14 14:05:40.442"},{"option":1,"created_at": "2024-11-14 14:08:19.524"},{"option":1,"created_at": "2024-11-14 14:41:51.433"},{"option":1,"created_at": "2024-11-14 14:42:58.883"},{"option":2,"created_at": "2024-11-14 14:07:06.391"},{"option":2,"created_at": "2024-11-14 14:20:05.328"},{"option":2,"created_at": "2024-11-14 14:20:25.874"}]
```

## Storing data
The app has an `application.properties` file setup to use postgres. By default it uses the username/password of `postgres`. Which is also used by the docker compose file provided.

```bash
docker compose up -d db
```

## Running
Ensure that postgres is running and the `application.properties` file has the correct details.
```bash
mvn spring-boot:run
```

## Testing
```bash
mvn test -Dtest=PollControllerTest.java
```

## Notes
Each route is annotated with CORS and allows access from any origin ie `*`. You should change this to allow specific origins in any sort of production environment.