package br.dev.filipov.ecommerce;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

public class CategorizadorDeProdutos {

    public static void main(String[] args) {

        var reader = new Scanner(System.in);

        System.out.println("Digite as categorias válidas: ");
        var categorias = reader.nextLine();

        while(true) {
            System.out.println("\nDigite o nome do produto: ");
            var user = reader.nextLine();

            var system = """
                    Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
                            
                    Escolha uma categoria dentra a lista abaixo:
                            
                    %s
                            
                    ###### Exemplo de uso:
                            
                    Pergunta: Bola de futebol
                    Resposta: Esportes
                    
                    ###### Regras a serem seguidas:
                    Caso o usuário pergunte algo que não seja de categorização de produtos, você deve responder que não pode ajudar, pois o seu papel é apenas responder a categoria dos produtos
                    """.formatted(categorias);

            dispararRequisicao(user, system);
        }
    }

    private static void dispararRequisicao(String user, String system) {
        var key = System.getenv("OPENAI_API_KEY");
        var service = new OpenAiService(key, Duration.ofSeconds(30));

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
