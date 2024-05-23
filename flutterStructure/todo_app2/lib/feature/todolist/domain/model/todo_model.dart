class TodoModel{
  final String title;
  final String description;

  TodoModel ({
    required this.title,
    required this.description
});
  Map<String, dynamic> toJson() {
    return {
      'title': title,
      'description': description,
    };
  }
}