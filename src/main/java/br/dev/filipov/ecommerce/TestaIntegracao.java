package br.dev.filipov.ecommerce;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.util.Arrays;

public class TestaIntegracao {

    public static void main(String[] args) {
        var user = "Gere 5 produtos";
        var system = "Você é um gerador de produtos fictícios para um e-commerce e deve gerar apenas o nome dos produtos solicitados pelo usuário.";

        var chave = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(chave);

        var completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .build();

        service
                .createChatCompletion(completionRequest)
                .getChoices()
                .forEach(c -> System.out.print(c.getMessage().getContent()));
    }

}
