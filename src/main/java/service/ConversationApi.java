/*
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package service;

import java.util.Map;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;
import java.util.*;
import jersey.repackaged.jsr166e.CompletableFuture;
import model.Resposta;

/**
 * Example of how to call the {@link ConversationService#message(String, MessageRequest)} method synchronous,
 * asynchronous, and using react.
 *
 * @version v1-experimental
 */
public class ConversationApi {
  private static String _Message = "";
  private static List<String> _Result;
  private static MessageResponse _LastResponse;
  
  public static Resposta GetResposa(Resposta pergunta) throws Exception {
    if (pergunta.getDescResposta() == "")
    {
    	_LastResponse = null;
    }
	  if (_Message == null || _Message == "");
	  {
		  _Message = "Oi";
	  }
	  _Message =   pergunta.getDescResposta();
	
	Resposta resposta = new Resposta();
	
	ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2017_02_03);
    service.setUsernameAndPassword("46cd8050-1cab-4f52-96be-1ee62098e596", "HEbdacsfbSLi");

    // sync
    MessageRequest newMessage = new MessageRequest.Builder().inputText(_Message).build();
   
    if (_LastResponse != null)
    {
       newMessage = new MessageRequest.Builder().inputText(_Message).context(_LastResponse.getContext()).build();
    }
    MessageResponse response = service.message("4a05e64b-1322-4968-a9cf-fa18cbf0b148", newMessage).execute();
   
    _Result = response.getText();
    //System.out.println(response);
    
    // async
    service.message("4a05e64b-1322-4968-a9cf-fa18cbf0b148", newMessage).enqueue(new ServiceCallback<MessageResponse>() {
      
      public void onResponse(MessageResponse response) {
    	  _Result = response.getText();
    	    //System.out.println(response);
      }

      
      public void onFailure(Exception e) { }
    });

    // rx callback
    service.message("4a05e64b-1322-4968-a9cf-fa18cbf0b148", newMessage).rx()
        .thenApply(new CompletableFuture.Fun<MessageResponse, Map<String, Object>>() {
          
          public Map<String, Object> apply(MessageResponse message) {
        	  return message.getOutput();
        	    //System.out.println(response);  
          ///return message.getOutput();
            
          }
        }).thenAccept(new CompletableFuture.Action<Map<String, Object>>() {
          
          public void accept(Map<String, Object> output) {
            System.out.println(output);
          }
        });

    // rx async callback
    service.message("4a05e64b-1322-4968-a9cf-fa18cbf0b148", newMessage).rx()
        .thenApplyAsync(new CompletableFuture.Fun<MessageResponse, Map<String, Object>>() {
          
          public Map<String, Object> apply(MessageResponse message) {
            return message.getOutput();
          }
        }).thenAccept(new CompletableFuture.Action<Map<String, Object>>() {
         
          public void accept(Map<String, Object> output) {
            System.out.println(output);
          }
        });

    // rx sync
    MessageResponse rxMessageResponse = service.message("4a05e64b-1322-4968-a9cf-fa18cbf0b148", newMessage).rx().get();
    System.out.println(rxMessageResponse);
    _LastResponse = response;
    
    resposta.setDescResposta(_Result.get(0));
    return resposta;
  }

}
