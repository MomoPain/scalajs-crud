package com.tierline.example.scalajs.react.client

import autowire._

import scala.scalajs.js.annotation.JSExport
import scala.annotation.meta.field
import com.tierline.example.scalajs.react.shared.{ Api, TodoItem }
import com.tierline.example.scalajs.react.shared.model.TodoItem2
import com.tierline.example.scalajs.react.shared.Foo
import scala.concurrent.ExecutionContext.Implicits.global
//import scala.concurrent.ExecutionContext.Implicits.global
//import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import boopickle._

@JSExport
object Client {

  @JSExport
  def run() = {
    println("run!!!")
  }
  //  @JSExport
  //  def update() = {
  //    AjaxClient[Api].updateTodo(TodoItem("", "")).call()
  //  }
  //
  @JSExport
  def get() = Seq(new TodoItem("1", "")) //AjaxClient[Api].getTodos().call()
  //
  //  @JSExport
  //  def delete(itemId: String) = AjaxClient[Api].deleteTodo(itemId).call()

}

//case class TodoItem2(id: String, text: String)
//case class TodoItem(id:goString, text: String)