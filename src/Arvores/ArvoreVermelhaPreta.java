package Arvores;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ArvoreVermelhaPreta extends DesenhoArvore {

    public ArvoreVermelhaPreta() {

    }

    public ArvoreVermelhaPreta(List<Node> listaNode) {
        this.listaNode = listaNode;
    }

    @Override
    public void put(int valor) {
        raiz = inserir(raiz, valor);

        if (raiz != null) {
            raiz.setCor(EngineFrame.BLACK);
        }

        atualizarPosicoes(raiz, 400, 120, distanciaX * 0.6);
        
    }

    private Node inserir(Node atual, int valor) {
        if (atual == null) {
            Node novo = new Node(valor, EngineFrame.RED, 0, 0);
            listaNode.add(novo);
            return novo;
        }

        if (valor < atual.getValor()) {
            atual.setFilhoEsquerda(inserir(atual.getFilhoEsquerda(), valor));
        } else if (valor > atual.getValor()) {
            atual.setFilhoDireita(inserir(atual.getFilhoDireita(), valor));
        } else {
            return atual;
        }

        atual = corrigirCoresERotacoes(atual);

        return atual;
    }

    private boolean verificarVermelho(Node n) {
        if (n == null) {
            return false;
        }
        return n.getCor() == EngineFrame.RED;
    }

    private Node rotacaoEsquerda(Node a) {
        Node b = a.getFilhoDireita();
        a.setFilhoDireita(b.getFilhoEsquerda());
        b.setFilhoEsquerda(a);
        b.setCor(a.getCor());
        a.setCor(EngineFrame.RED);
        
        atualizarPosicoes(raiz, 400, 120, distanciaX * 0.6); 
        return b;
    }

    private Node rotacaoDireita(Node a) {
        Node b = a.getFilhoEsquerda();
        a.setFilhoEsquerda(b.getFilhoDireita());
        b.setFilhoDireita(a);
        b.setCor(a.getCor());
        a.setCor(EngineFrame.RED);
        
        atualizarPosicoes(raiz, 400, 120, distanciaX * 0.6); 
        return b;
    }

    private void inverterCores(Node n) {
        n.setCor(EngineFrame.RED);
        if (n.getFilhoEsquerda() != null) n.getFilhoEsquerda().setCor(EngineFrame.BLACK);
        if (n.getFilhoDireita() != null) n.getFilhoDireita().setCor(EngineFrame.BLACK);
    }

    @Override
    public void delete(int valor) {
        raiz = remover(raiz, valor);
        if (raiz != null) {
            raiz.setCor(EngineFrame.BLACK);
        }
        atualizarPosicoes(raiz, 400, 120, distanciaX * 0.6);
    }

    private Node remover(Node atual, int valor) {
        if (atual == null) {
            return null;
        }

        if (valor < atual.getValor()) {
            atual.setFilhoEsquerda(remover(atual.getFilhoEsquerda(), valor));
        } else if (valor > atual.getValor()) {
            atual.setFilhoDireita(remover(atual.getFilhoDireita(), valor));
        } else {

            if (atual.getFilhoEsquerda() == null) {
                listaNode.remove(atual);
                return atual.getFilhoDireita();
            }
            if (atual.getFilhoDireita() == null) {
                listaNode.remove(atual);
                return atual.getFilhoEsquerda();
            }

            Node sucessor = getMenor(atual.getFilhoDireita());

            atual.setValor(sucessor.getValor());

            atual.setFilhoDireita(remover(atual.getFilhoDireita(), sucessor.getValor()));
        }

        atual = corrigirCoresERotacoes(atual);

        return atual;
    }

    private Node corrigirCoresERotacoes(Node node) {

        if (verificarVermelho(node.getFilhoDireita()) && !verificarVermelho(node.getFilhoEsquerda())) {
            node = rotacaoEsquerda(node);
        }

        if (verificarVermelho(node.getFilhoEsquerda()) && node.getFilhoEsquerda() != null && verificarVermelho(node.getFilhoEsquerda().getFilhoEsquerda())) {
            node = rotacaoDireita(node);
        }

        if (verificarVermelho(node.getFilhoEsquerda()) && verificarVermelho(node.getFilhoDireita())) {
            inverterCores(node);
        }

        return node;
    }

    @Override
    public void transformacao1(List<Node> listaNode) {
        ArvoreBinaria nova = new ArvoreBinaria();
        for (Integer v : getValores()) {
            nova.put(v);
        }
        transformacao(new PainelArvores("Árvore Binária de Busca", nova));
    }

    @Override
    public void transformacao2(List<Node> listaNode) {
        ArvoreAVL nova = new ArvoreAVL();
        for (Integer v : getValores()) {
            nova.put(v);
        }
        transformacao(new PainelArvores("Árvore AVL", nova));
    }

    private void transformacao(EngineFrame e) {
        e.setIconImage(logo.buffImage);
        e.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}