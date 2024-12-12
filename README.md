# Blog App REST APIs

## REST APIs for Post Resource

| HTTP Method | URL Path                                           | Status Code     | Description                                  |
|-------------|----------------------------------------------------|-----------------|----------------------------------------------|
| GET         | `/api/posts`                                      | 200 (OK)        | Get all posts                                |
| GET         | `/api/posts/{id}`                                 | 200 (OK)        | Get post by Id                               |
| POST        | `/api/posts`                                      | 201 (Created)   | Create a new post                            |
| PUT         | `/api/posts/{id}`                                 | 200 (OK)        | Update existing post with Id                |
| DELETE      | `/api/posts/{id}`                                 | 200 (OK)        | Delete post by Id                            |
| GET         | `/api/posts?pageSize=5&pageNo=1&sortBy=firstName` | 200 (OK)        | Paginating and sorting posts                |
