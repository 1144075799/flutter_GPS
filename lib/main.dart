import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'GPS',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'GPS导航'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {


  String _message='检查GPS状态中......';

  ///////////Flutter 调用原生 Start//////////////

  static const MethodChannel methodChannel=
  MethodChannel('samples.flutter.io/gps');

  Future<void> _inspection()async{
    String message;
    message=await methodChannel.invokeMethod('inspectionGPS');
    setState(() {
      _message=message;
    });
  }
  //////// Flutter 调用原生  End  ////////

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: new Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            new Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Text('GPS状态:'),
                Text(
                  _message
                )
              ],
            ),
            Padding(
              padding: EdgeInsets.all(10.0),
              child: RaisedButton(
                color: Colors.blue,
                textColor: Colors.white,
                child: Text('检查GPS状态'),
                onPressed: _inspection,
              ),
            )
          ],
        ),
      ),
    );
  }
}
