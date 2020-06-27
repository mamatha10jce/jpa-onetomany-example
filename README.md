# jpa-onetomany-example
Example app which shows hibernate one to many mapping

how to map a one-to-many database relationship at the object level using JPA and Hibernate.

Consider the following two tables - posts and comments of a Blog database schema where the posts table has a one-to-many relationship with the comments table -

Creating the Project
If you have Spring Boot CLI installed, then you can type the following command in your terminal to generate the project -

spring init -n=jpa-one-to-many-demo -d=web,jpa,mysql --package-name=com.example.jpa jpa-one-to-many-demo
Alternatively, You can generate the project from Spring Initializr web tool by following the instructions below -

Go to http://start.spring.io
Enter Artifact as “jpa-one-to-many-demo”
Click Options dropdown to see all the options related to project metadata.
Change Package Name to “com.example.jpa”
Select Web, JPA and Mysql dependencies.
Click Generate to download the project.

The best way to model a one-to-many relationship in hibernate
I have been working with hibernate for quite some time and I’ve realized that the best way to model a one-to-many relationship is to use just @ManyToOne annotation on the child entity.

The second best way is to define a bidirectional association with a @OneToMany annotation on the parent side of the relationship and a @ManyToOne annotation on the child side of the relationship. The bidirectional mapping has its pros and cons. I’ll demonstrate these pros and cons in the second section of this article. I’ll also tell you when a bidirectional mapping is a good fit.

But let’s first model our one-to-many relationship in the best way possible.

Defining the Domain Models
In this section, we’ll define the domain models of our application - Post and Comment.

Note that both Post and Comment entities contain some common auditing related fields like created_at and updated_at.

We’ll abstract out these common fields in a separate class called AuditModel and extend this class in the Post and Comment entities.

We’ll also use Spring Boot’s JPA Auditing feature to automatically populate the created_at and updated_at fields while persisting the entities.

You can run the application by typing the following command in the terminal -

mvn spring-boot:run

Sample results:

Post: http://localhost:8080/posts
{
	"title": "post-1",
	"description": "post-1 description",
	"content": "post-1 content"
}

o/p:
{
    "createdAt": "2020-06-27T22:39:36.633+00:00",
    "updatedAt": "2020-06-27T22:39:36.633+00:00",
    "id": "55969ac5-19fb-4c83-aa5b-bb7d1ec96843",
    "content": "post-1 content",
    "description": "post-1 description",
    "title": "post-1"
}

Get: http://localhost:8080/posts?page=0&size=2&sort=createdAt,desc

{
    "content": [
        {
            "createdAt": "2020-06-27T22:39:36.633+00:00",
            "updatedAt": "2020-06-27T22:39:36.633+00:00",
            "id": "55969ac5-19fb-4c83-aa5b-bb7d1ec96843",
            "content": "post-1 content",
            "description": "post-1 description",
            "title": "post-1"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 2,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 2,
    "first": true,
    "numberOfElements": 1,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "number": 0,
    "empty": false
}

Post:http://localhost:8080/posts/55969ac5-19fb-4c83-aa5b-bb7d1ec96843/comments
{
	"text": "nice post mamatha"
}

output:
{
    "createdAt": "2020-06-27T22:43:46.948+00:00",
    "updatedAt": "2020-06-27T22:43:46.948+00:00",
    "id": "6ba9ba82-050f-43ca-b0f6-af9b48dd1f35",
    "text": "nice post mamatha"
}

Get: http://localhost:8080/posts/55969ac5-19fb-4c83-aa5b-bb7d1ec96843/comments

{
    "content": [
        {
            "createdAt": "2020-06-27T22:43:46.948+00:00",
            "updatedAt": "2020-06-27T22:43:46.948+00:00",
            "id": "6ba9ba82-050f-43ca-b0f6-af9b48dd1f35",
            "text": "nice post mamatha"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 20,
        "paged": true,
        "unpaged": false
    },
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 20,
    "first": true,
    "numberOfElements": 1,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "number": 0,
    "empty": false
}

Note: you can perform all CRUD operations.
