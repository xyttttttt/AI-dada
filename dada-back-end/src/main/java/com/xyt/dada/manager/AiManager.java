package com.xyt.dada.manager;

import com.xyt.dada.common.ErrorCode;
import com.xyt.dada.exception.BusinessException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.*;
import io.reactivex.Flowable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class AiManager {

    @Resource
    private ClientV4 clientV4;

    private static final Float STABLE_TEMPERATURE = 0.05F;

    private static final Float UNSTABLE_TEMPERATURE = 0.96F;



    /**
     * 同步请求（答案较稳定）
     * @param sysMessage
     * @param userMessage
     * @return
     */
    public String doSyncStableRequest(String sysMessage,String userMessage) {
        return doRequest(sysMessage,userMessage,Boolean.FALSE,STABLE_TEMPERATURE);
    }



    /**
     * 同步请求（答案不稳定）
     * @param sysMessage
     * @param userMessage
     * @return
     */
    public String doSyncUnStableRequest(String sysMessage,String userMessage) {
        return doRequest(sysMessage,userMessage,Boolean.FALSE,UNSTABLE_TEMPERATURE);
    }


    /**
     * 通用请求 同步请求
     * @param sysMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public String doSyncRequest(String sysMessage,String userMessage,Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage sysChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), sysMessage);
        chatMessageList.add(sysChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doRequest(chatMessageList,Boolean.FALSE,temperature);
    }

    /**
     * 通用请求，简化消息传递
     * @param sysMessage
     * @param userMessage
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(String sysMessage,String userMessage,Boolean stream,Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage sysChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), sysMessage);
        chatMessageList.add(sysChatMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(userChatMessage);
        return doRequest(chatMessageList,stream,temperature);
    }


    /**
     * 通用流式请求，简化消息传递
     * @param sysMessage
     * @param userMessage
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(String sysMessage,String userMessage,Float temperature) {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        ChatMessage sysChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), sysMessage);
        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        chatMessageList.add(sysChatMessage);
        chatMessageList.add(userChatMessage);
        return doStreamRequest(chatMessageList,temperature);
    }


    /**
     * 通用请求
     * @param message
     * @param stream
     * @param temperature
     * @return
     */
    public String doRequest(List<ChatMessage> message,Boolean stream,Float temperature) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), message);
        messages.add(chatMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(stream)
                .invokeMethod(Constants.invokeMethod)
                .temperature(temperature)
                .messages(message)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).toString();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }

    /**
     * 通用流式请求
     * @param message
     * @param temperature
     * @return
     */
    public Flowable<ModelData> doStreamRequest(List<ChatMessage> message, Float temperature) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .invokeMethod(Constants.invokeMethod)
                .temperature(temperature)
                .messages(message)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getFlowable();
        }catch (Exception e){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }
}
