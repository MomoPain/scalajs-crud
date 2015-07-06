# scalajs-react-crud

Sample project for single page web application with [React] http://facebook.github.io/react/ and [Scala.js](https://github.com/scala-js/scala-js).

This project is made up of "AS React file" and "Ajax of the API that have been compiled with Scala.js".

That means, The View layer is utilized in React(not use Scala.js), To exchange data with server by implemented api in Scala.js.
View layer is reference React Comment Tutorial and it is to change exchange data with server of code. 

# Usage

```
$ cd scalajs-react-crud
$ sbt
```
```

> compileAll  // Build the client JS file and server side, and copy js files to server webapp directory.
> container:start // Start the server
```


# Component
- [React](http://facebook.github.io/react/)
- [Scala.js](https://github.com/scala-js/scala-js)
- [autowire](https://github.com/lihaoyi/autowire)
- [scalatra](http://scalatra.org/)
- [scalate](http://scalate.github.io/scalate/)

# Reference projects
- [React Tutorial](http://facebook.github.io/react/docs/tutorial.html)
- [scalajs-spa-tutorial](https://github.com/ochrons/scalajs-spa-tutorial)
- [scalaWUI](https://github.com/mathieuleclaire/scalaWUI)
- [scalatra-template](https://github.com/takezoe/scalatra-scalajs-template)
