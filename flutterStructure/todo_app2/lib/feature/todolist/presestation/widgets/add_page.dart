import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:todo_app2/feature/todolist/application/service/todo_service.dart';
import 'package:todo_app2/feature/todolist/domain/model/todo_model.dart';


class AddPage extends StatefulWidget {
  const AddPage({super.key});

  @override
  State<AddPage> createState() => _AddPageState();
}

class _AddPageState extends State<AddPage> {
  TextEditingController titleController = TextEditingController();
  TextEditingController descripController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    final TodoService todoService = TodoService();
    return Scaffold(
      appBar: AppBar(
        title: Text('Add todo'),
      ),
      body: ListView(
        padding: EdgeInsets.all(20),
        children: [
          TextField(
            controller: titleController,
            decoration: InputDecoration(hintText: 'Title'),
          ),
          TextField(
            controller: descripController,
            decoration: InputDecoration(hintText: 'Description'),
            keyboardType: TextInputType.multiline,
            minLines: 5,
            maxLines: 8,
          ),
          SizedBox(height: 20,),
          ElevatedButton(
              onPressed: (){
                final title = titleController.text;
                final descrip = descripController.text;
                final todo = TodoModel(title: title, description: descrip);
                todoService.submitData(todo);},
              child: Text('Submit'))
        ],
      ),
    );
  }

}