package org.example.gestiondepassementplafond.services.template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class GenerationFicheService {

    @Value("${server1.template}")
    String dossierTemplate;

    private final TemplateEngine templateEngine;

    public GenerationFicheService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String generateHtml(Map<String, String> listVariable, String templateName){
        Context context = new Context();
        for (Map.Entry<String, String> entry : listVariable.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return templateEngine.process(dossierTemplate+templateName, context);
    }

}
