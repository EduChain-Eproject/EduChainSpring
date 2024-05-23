import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:todo_app2/feature/todolist/domain/model/todo_model.dart';
class TodoService {

  TodoService();
  Future<void> submitData(TodoModel todo) async {
    //get data from form
    final body = {
      "title" : todo.title,
      "description": todo.description,
      "is_complete": false
    };

    final url = "http://10.0.2.2:8080/api/add";
    final uri = Uri.parse(url);
    final jsonBody = todo.toJson();
    final response = await http.post(uri,
        headers: {
          'Content-Type': 'application/json'
        }, body: jsonEncode(jsonBody));
    print(response.statusCode);
    print(response.body);

  }

}