package be.vdab;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
@Theme("mytheme")
@Widgetset("be.vdab.MyAppWidgetset")
public class MyUI extends UI {
    int guesses = 3;
    int randomNumber = generate();
    Button reset = new Button("Reset");
    TextField textField = new TextField();

    Button button = new Button("Submit your guess!");
    Label guessANumber = new Label("Guess a number between 1 and 10. You have 3 chances!");
    Label info = new Label("");

    public int generate() {
        return randomNumber;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);


        reset.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent clickEvent) {
                guesses = 3;
                layout.replaceComponent(reset, button);
                info.setCaption("");
                randomNumber = generate();

            }
        });
        button.addClickListener(new Button.ClickListener() {

                                    @Override
                                    public void buttonClick(ClickEvent event) {
                                        if (guesses != 0) {
                                            String clientGuess = textField.getValue();
                                            int parsedClientGuess = Integer.parseInt(clientGuess);
                                            if (parsedClientGuess == randomNumber) {
                                                info.setCaption("Congrats! You've guessed correctly");
                                                layout.replaceComponent(button, reset);
                                            } else {
                                                if (parsedClientGuess < randomNumber) {
                                                    info.setCaption("Auch! Try again, you've got " + (guesses - 1) + " guesses left! \n" +
                                                            "Here's a hint, you should aim higher!");
                                                } else
                                                    info.setCaption("Auch! Try again, you've got " + (guesses - 1) + " guesses left! \n" +
                                                            "Here's a hint, you should aim lower!");
                                            }
                                        }
                                        guesses = guesses - 1;
                                        if (guesses == 0) {
                                            layout.replaceComponent(button, reset);
                                            info.setCaption("Game over. The answer was " + randomNumber);
                                        }

                                    }
                                }
        );
        layout.addComponent(guessANumber);
        layout.addComponent(textField);
        layout.addComponent(info);
        layout.addComponent(button);





    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
