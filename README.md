Basic REST API that displays and allows manipulation of todo list items.

Database setup in application.properties:
- user: postgres
- password: beata
- database: todo

Tested with Postman/curl. Before running the service create the todo database (should be empty). Entry point is localhost:2020/items.

GET /items/
Lists all items and categories assigned to these items

POST /items/
Creates new list item with a given name

PUT /items/{id}
Updates item name

GET /items/{id}
Displays item

DELETE /items/{id}
Deletes item

PUT /items/{itemId}/addcategory/{categoryId}
Creates a link between given item and category

PUT /items/{itemId}/removecategory/{categoryId}
Removes the link between given item and category

GET /categories/
Lists all categories with item ids attached

POST /categories/
Creates new category with a given name

PUT /categories/{id}
Updates category name

GET /categories/{id}
Displays category with id

DELETE /category/{id}
Deletes category
