Basic Rest api that displays and allows manipulation of todo list items.

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
