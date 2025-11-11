package Main;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import Arvores.ArvoreAVL;
import Arvores.ArvoreBinariaBusca;
import Arvores.ArvoreVermelhaPreta;
import Arvores.JanelaArvore;
import br.com.davidbuzatto.jsge.imgui.GuiButton;
import br.com.davidbuzatto.jsge.imgui.GuiComponent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de projeto básico da JSGE.
 *
 * JSGE basic project template.
 *
 * @author Prof. Dr. David Buzatto
 */

public class JanelaPrincipal extends EngineFrame {
       
    private GuiButton botaoArvoreAVL;
    private GuiButton botaoArvoreBinaria;
    private GuiButton botaoArvoreRedBlack;
    
    private List<GuiComponent> listaBotoes;
    
    private final Color corBotao = new Color(0, 0, 0);
    
    public JanelaPrincipal() {
        
         super(
                800, // largura                      / width
                620, // algura                       / height
                "Simulador Árvores - João Davi", // título                       / title
                60, // quadros por segundo desejado / target FPS
                true, // suavização                   / antialiasing
                false, // redimensionável              / resizable
                false, // tela cheia                   / full screen
                false, // sem decoração                / undecorated
                false, // sempre no topo               / always on top
                false // fundo invisível              / invisible background
        );
        
    }
    
    @Override
    public void create() {
        
       useAsDependencyForIMGUI();
        
        botaoArvoreAVL = new GuiButton(297, 300, 200, 120, "ÁRVORE AVL" );
        botaoArvoreBinaria = new GuiButton(50, 300, 200, 120, "ÁRVORE BINÁRIA" );
        botaoArvoreRedBlack = new GuiButton(545, 300, 200, 120, "ÁRVORE VERMELHA-PRETO" );
        
        listaBotoes = new ArrayList<>();
        
        listaBotoes.add(botaoArvoreAVL);
        listaBotoes.add(botaoArvoreBinaria);
        listaBotoes.add(botaoArvoreRedBlack);
        
    }
    
    @Override
    public void update(double delta) {
        
        for (GuiComponent botoes : listaBotoes) {
            botoes.update(delta);
            botoes.setBackgroundColor(corBotao);
            botoes.setTextColor(WHITE);
        }
        
        if (isMouseButtonPressed(MOUSE_BUTTON_LEFT)) {
            
            if (botaoArvoreBinaria.isMousePressed()) {
                abrirJanela(new JanelaArvore("Árvore Binária de Busca", new ArvoreBinariaBusca()));
            } else if (botaoArvoreAVL.isMousePressed()) {
                abrirJanela(new JanelaArvore("Árvore AVL", new ArvoreAVL()));
            } else if (botaoArvoreRedBlack.isMousePressed()) {
                abrirJanela(new JanelaArvore("Árvore Vermlha e Preta", new ArvoreVermelhaPreta()));
            }
        }
        
    }
    
    @Override
    public void draw() {
        
       clearBackground(WHITE);
        
        setFontName(FONT_SANS_SERIF);
        setFontStyle(FONT_BOLD);
       
        for(GuiComponent botoes : listaBotoes) {
            botoes.draw();
        }
        
        drawText("SIMULAÇÕES ÁRVORES", 95, 170, 50, BLACK);
        drawText("by: João Davi", 650, 600, 20, BLACK);
    }

    
    private void abrirJanela(EngineFrame janela) {
        janela.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new JanelaPrincipal();
    }
    
}