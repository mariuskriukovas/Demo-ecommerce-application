# Demo E-Commerce Application 

## Overview
The e-commerce application is a web-based platform that allows customers to browse and purchase products online. The application is designed using a web service architecture, which provides a fast, secure, and scalable experience for its users, while also promoting maintainability and flexibility in its development and deployment.

## Architecture
The e-commerce application is built using a three-tier architecture, consisting of the following layers:
* **Presentation Layer:** This layer is responsible for presenting the user interface to the customer. It is implemented using Vue.js JavaScript framework supplemented with Vuetify library. The presentation layer communicates with the application layer via RESTful APIs.
* **Application Layer:** This layer contains the business logic of the application. It is implemented using a Java programing language, Spring framework, and additional libraries i.e. Hibernate, Eventuate and etc. The application layer services communicate with each other using Apache Kafka distributed event streaming platform and RESTful APIs. Communication with the data layer is achieved using standard TCP/IP  protocol. Services implement create, read, update, and delete (CRUD) operations using a model–view–controller (MVC) pattern. 
* **Data Layer:** This layer contains the data storage and retrieval mechanisms for the application. It is implemented using fast, open source, embedded H2 Database.


## Components
*	**Inventory Service** – responsible for managing the stock levels of the products in the store’s inventory. 


## TODO
```diff
- Implement and describe multiple services 
```
