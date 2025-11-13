package Arvores;

import br.com.davidbuzatto.jsge.core.Camera2D;
import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import static br.com.davidbuzatto.jsge.core.engine.EngineFrame.*;
import br.com.davidbuzatto.jsge.geom.Rectangle;
import br.com.davidbuzatto.jsge.geom.RoundRectangle;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiInputDialog;
import br.com.davidbuzatto.jsge.math.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class PainelArvores extends EngineFrame {

    private Camera2D camera;
    private Rectangle bordaCamera;
    private Zoom foco;
    
    private GuiButton botaoPut;
    private GuiButton botaoDelete;
    private GuiButton botaoLimpar;    

    private GuiInputDialog inputValores;

    private List<GuiButton> listaBotoes;

    private boolean exibirInput;
    private boolean isPutting;

    private DesenhoArvore arvore;
    
    private final Color corBotao = new Color(0, 0, 0);
    private final int BOTAO_LARGURA = 80;
    private final int BOTAO_ALTURA = 35;
    private final int MARGEM_X = 10;
    private final int MARGEM_Y = 15;


    public PainelArvores(String title, DesenhoArvore arvore) {
        super(
                800, // largura
                900, // altura
                title, // título
                60, // target FPS
                true, // antialiasing
                false, // resizable
                false, // full screen
                false, // undecorated
                false, // always on top
                false // invisible background
        );

        this.arvore = arvore;
    }

    @Override
    public void create() {

        useAsDependencyForIMGUI();

        exibirInput = false;

        int xInicio = MARGEM_X;
        int yAtual = MARGEM_Y;

        botaoPut = new GuiButton(xInicio, yAtual, BOTAO_LARGURA, BOTAO_ALTURA, "PUT");
        yAtual += BOTAO_ALTURA + 10; // Próximo botão abaixo

        botaoDelete = new GuiButton(xInicio, yAtual, BOTAO_LARGURA, BOTAO_ALTURA, "DELETE");
        yAtual += BOTAO_ALTURA + 10; // Próximo botão abaixo

        botaoLimpar = new GuiButton(xInicio, yAtual, BOTAO_LARGURA, BOTAO_ALTURA, "LIMPAR");

        listaBotoes = new ArrayList<>();
        listaBotoes.add(botaoLimpar);
        listaBotoes.add(botaoPut);
        listaBotoes.add(botaoDelete);

        inputValores = new GuiInputDialog("Input", "Entre com um número Inteiro", true);

        foco = new Zoom(new Vector2(getScreenWidth() / 2, getScreenHeight() / 2), new Vector2(10, 10), 500);
        camera = new Camera2D(new Vector2(foco.pos.x, foco.pos.y), new Vector2(0, 0), 0, 1);
        bordaCamera = new Rectangle(0, 0, getScreenWidth(), getScreenHeight());
    }

    @Override
    public void update(double delta) {

        for (GuiButton b : listaBotoes) {
            b.update(delta);
            b.setTextColor(WHITE);
            b.setBackgroundColor(corBotao);
            b.setEnabled(true);
            b.setVisible(true);
        }

        inputValores.update(delta);
        
        if (!exibirInput) {

            foco.update(delta, bordaCamera, this);

            if (isKeyDown(KEY_KP_ADD) || isKeyDown(KEY_EQUAL)) {
                camera.zoom += 0.01;
            } else if (isKeyDown(KEY_KP_SUBTRACT) || isKeyDown(KEY_MINUS)) {
                camera.zoom -= 0.01;
                if (camera.zoom < 0.1) {
                    camera.zoom = 0.1;
                }
            }

            if (isKeyPressed(KEY_R)) {
                camera.rotation = 0;
                camera.zoom = 1;
                foco.pos.x = getScreenWidth() / 2;
                foco.pos.y = getScreenHeight() / 2;
            }
        }

        if (botaoPut.isMousePressed()) {
            inputValores.show();
            exibirInput = true;
            isPutting = true; 
        } else if (botaoDelete.isMousePressed()) {
            inputValores.show();
            exibirInput = true;
            isPutting = false; 
        } else if (botaoLimpar.isMousePressed()) {
            arvore.limpar();
        }

        if (inputValores.isOkButtonPressed() || inputValores.isEnterKeyPressed()) {
            inputValores.hide();
            exibirInput = false;
            if (checarInteiro(inputValores.getValue())) {
                if (isPutting) {
                    arvore.put(Integer.parseInt(inputValores.getValue()));
                } else {
                    arvore.delete(Integer.parseInt(inputValores.getValue()));
                }
            }
        } else if (inputValores.isCancelButtonPressed() || inputValores.isCloseButtonPressed()) {
            inputValores.hide();
            exibirInput = false;
        }

        atualizarCamera();
    }

    @Override
    public void draw() {

        setFontName(FONT_SANS_SERIF);
        setFontStyle(FONT_BOLD);

        beginMode2D(camera);
        arvore.drawArvore(this);
        endMode2D();

        inputValores.draw();

        String textoAltura = "Altura: " + String.valueOf(arvore.getAlturaArvore(arvore.getRaiz()));

        double larguraTexto = measureText(textoAltura, 20);
        double xCentralizado = (getScreenWidth() - larguraTexto) / 2;
        
        drawText(textoAltura, xCentralizado, MARGEM_Y + 10, 20, BLACK);

        for (GuiButton b : listaBotoes) {
            b.draw();
        }
    }

    protected void atualizarCamera() {
        camera.target.x = foco.pos.x;
        camera.target.y = foco.pos.y;
        camera.offset.x = getScreenWidth() / 2;
        camera.offset.y = getScreenHeight() / 2;
    }

    protected boolean mouseIn(RoundRectangle r) {
        double mouseX = getMouseX();
        double mouseY = getMouseY();
        return mouseX >= r.x && mouseX <= r.width + r.x
                && mouseY >= r.y && mouseY <= r.height + r.y;
    }

    private boolean checarInteiro(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}