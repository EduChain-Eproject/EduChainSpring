import 'package:flutter/material.dart';

import '../feature/todolist/presestation/widgets/add_page.dart';

class RoutePage {
  RoutePage();
  //route add page
  void NavigateToAddPage(BuildContext context){
    final route = MaterialPageRoute(
        builder: (context) => AddPage()
    );
    Navigator.push(context , route);
  }
}

