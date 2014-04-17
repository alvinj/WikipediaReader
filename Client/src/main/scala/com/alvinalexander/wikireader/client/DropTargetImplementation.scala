package com.alvinalexander.wikireader.client

import java.awt.dnd.DropTargetAdapter
import javax.swing.JFrame
import javax.swing.JPanel
import java.awt.dnd.DropTargetDropEvent
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTargetDragEvent
import java.io.IOException
import java.awt.datatransfer.UnsupportedFlavorException
import java.awt.Color
import java.awt.dnd.DropTargetEvent

class DropTargetImplementation(dropHandler: DropHandlerInterface, panel: JPanel) extends DropTargetAdapter
{

  def drop(e: DropTargetDropEvent) {
      // Called when the user finishes or cancels the drag operation.
      val transferable = e.getTransferable
      try {
          if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
              e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE)
              val url = transferable.getTransferData(DataFlavor.stringFlavor)
              e.getDropTargetContext.dropComplete(true)
              dropHandler.handleDropEvent(url.toString)
          } else {
              e.rejectDrop
          }
      }
      catch {
          case ioe: IOException => 
              //ioe.printStackTrace
              e.rejectDrop
          case ufe: UnsupportedFlavorException =>
              //ufe.printStackTrace
              e.rejectDrop
      }
  }
  
  override def dragEnter(e: DropTargetDragEvent) {
      // called when the user is dragging and enters our target
      panel.setBackground(Color.GREEN);
  }

  override def dragExit(e: DropTargetEvent) {
      // called when the user is dragging and leaves our target
      panel.setBackground(Color.WHITE)
  }

  override def dragOver(e: DropTargetDragEvent) {
      // called when the user is dragging and moves over our target
      panel.setBackground(Color.GREEN)
  }

}
 
  
  
  
  
  
