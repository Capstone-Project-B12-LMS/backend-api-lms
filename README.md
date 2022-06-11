# About

Here is `Backend API` project for out `LMS (Learning Management System)`. On this project we use `Java` as Programming
Language and `Spring Boot` as Framework.

Here we will user `GraphQL` as main API and possibly use `Rest FULL API`
if needed

# Entity Relational Database

- URL : https://dbdiagram.io/d/628e3deff040f104c196a2db

# How to run it

First clone this repository to your local storage.

```sh
git clone https://github.com/Capstone-Project-B12-LMS/backend-api-lms.git
cd backend-api-lms
```

To make it easier to run here i use `Docker`. So if you haven't installed `Docker`, please install
it [`here`](https://docs.docker.com/desktop/)

if you have installed `Docker` please run command bellow

```sh
cd env/
docker compose up -d
```

And `Docker` will magically prepare app enviroment.

Here we have 2 mode of API

- `Rest FULL API`

    - On Rest FULL API you can see documentation in `http://localhost:8080/restapi/docs/swagger-ui/index.html#/` after
      app running.

- `GraphQL API`

    - On GraphQL API you can access `http://localhost:8080/gql/v1/graphiql?path=/graphql` to use `GraphQL` in browser
      and you can send your `GraphQL Query` in `http://localhost:8080/gql/v1/graphql`

# Using Deployed App

This app already deployed in server enviroment. So, you can use some resource to use this app without install it.
Use resource bellow to use it.

- `Rest FULL API`

    - API Docs : http://ec2-34-212-169-254.us-west-2.compute.amazonaws.com/restapi/v1/docs/swagger-ui/index.html#/

- `GraphQL API`

    - GraphiQL to test graphql in browser with simple
      documentation : http://ec2-34-212-169-254.us-west-2.compute.amazonaws.com/gql/v1/graphiql?path=/gql/v1/graphql

    - GraphQL url : http://ec2-34-212-169-254.us-west-2.compute.amazonaws.com/gql/v1/graphql
