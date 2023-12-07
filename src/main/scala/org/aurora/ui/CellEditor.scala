package org.aurora.model



import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js

package object ui :
  enum EditorToggleState(colorString:String):
    case StateOne extends EditorToggleState("green")
    case StateTwo extends EditorToggleState("blue")
    
    def color = colorString
    def nextState = this match
      case StateOne => StateTwo
      case StateTwo => StateOne

  import EditorToggleState._  

  def cellTextInput: HtmlElement = 
    val v = Var("")
    val toggleState = Var(StateOne)

    div(
      child.text <-- v.signal,
      input(
        backgroundColor <-- toggleState.signal.map(_.color)),
        typ := "text",
        onInput.mapToValue --> v,
        onDblClick--> (_ => toggleState.update(_.nextState)),
      )
    