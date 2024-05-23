import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../../../route/route_pages.dart';

class TodoList extends StatefulWidget{
  const TodoList({super.key});
  @override
  State<TodoList> createState() => _TodoState ();
}
  class _TodoState extends State<TodoList>{
    final RoutePage routePage = RoutePage();
    @override
    Widget build(BuildContext context) {
      return Scaffold(
        appBar: AppBar(
          title: Text('Todo App'),
        ),
        floatingActionButton: FloatingActionButton.extended(
            onPressed: (){
              routePage.NavigateToAddPage(context);
            },
            label: Text('Todo')),
      );
    }
}
