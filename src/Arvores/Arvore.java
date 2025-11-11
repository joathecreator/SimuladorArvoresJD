package Arvores;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import br.com.davidbuzatto.jsge.image.Image;
import static br.com.davidbuzatto.jsge.image.ImageUtils.loadImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JottaD
 */
public abstract class Arvore {

    protected final Image logo = loadImage("resources/images/logoSDSS.png");
    protected Node raiz;
    protected List<Node> listaNode = new ArrayList<>();

    protected double distanciaX = 320;
    protected final double distanciaY = 160;

    public Arvore() {

    }

    public Arvore(List<Node> listaNode) {
        this.listaNode = listaNode;
    }

    public abstract void put(int valor);

    public abstract void delete(int valor);

    public abstract void transformacao1(List<Node> listaNode);

    public abstract void transformacao2(List<Node> listaNode);

    public void limpar() {
        listaNode.clear();
        raiz = null;
    }

    public void drawArvore(EngineFrame e) {

        if (listaNode.isEmpty()) {
            e.drawText("", (e.getScreenWidth() - e.measureText("", 30)) / 2, e.getScreenHeight() / 2, 30, EngineFrame.GRAY);
            return;
        }

        for (Node n : listaNode) {
            n.drawNode(e);
        }

    }

    public int getAlturaArvore(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(getAlturaArvore(node.getFilhoDireita()), getAlturaArvore(node.getFilhoEsquerda()));
    }

    public Node getRaiz() {
        return raiz;
    }

    public static void main(String[] args) {

        ArvoreBinaria arvore = new ArvoreBinaria();
    }

    public Node getMenor(Node n) {
        Node atual = n;
        while (atual.getFilhoEsquerda() != null) {
            atual = atual.getFilhoEsquerda();
        }
        return atual;
    }

    public List<Integer> getValores() {
        List<Integer> valores = new ArrayList<>();
        for (Node n : listaNode) {
            valores.add(n.getValor());
        }
        return valores;
    }

    public void atualizarPosicoes(Node atual, double x, double y, double desvioX) {
        if (atual == null) {
            return;
        }

        atual.setCentroX(x);
        atual.setCentroY(y);

        if (atual.getFilhoEsquerda() != null) {
            atualizarPosicoes(atual.getFilhoEsquerda(), x - desvioX, y + distanciaY, desvioX / 1.75);
        }

        if (atual.getFilhoDireita() != null) {
            atualizarPosicoes(atual.getFilhoDireita(), x + desvioX, y + distanciaY, desvioX / 1.75);
        }
    }

}