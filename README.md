Design and implement a RESTful API (including data model and the backing implementation) for
money transfers between accounts.
### Explicit requirements:
1. You can use Java or Kotlin.
2. Keep it simple and to the point (e.g. no need to implement any authentication).
3. Assume the API is invoked by multiple systems and services on behalf of end users.
4. You can use frameworks/libraries if you like (except Spring), but don't forget about
requirement #2 and keep it simple and avoid heavy frameworks.
5. The datastore should run in-memory for the sake of this test.
6. The final result should be executable as a standalone program (should not require a
pre-installed container/server).
7. Demonstrate with tests that the API works as expected.
### Implicit requirements:
1. The code produced by you is expected to be of high quality.
2. There are no detailed requirements, use common sense.

### General information
1. For simplicity
   1. there are no DTO <=> DAO translators
   2. there are only 2 accounts with 1 client that can execute internal transfers between his accounts
   3. there is no currency support
   4. I am aware there are no integration tests to not overextend the home task deadline

### How to run
```
mvn exec:java
```

Server runs on http://localhost:4567 with sample accounts with id's 1 and 2 which you can query.

### Endpoints
| Method | Path | Description |
| -----------| ------ | ------ |
| GET | /account/{accountId} | get account information by his id | 
| POST | /transfer | execute money transfer between 2 accounts given body as below | 

Transfer request body
```
{
    "from": 2,
    "to": 1,
    "amount": 10.00
}
```
### How to run tests
Maven is required to run tests
```
mvn test
```