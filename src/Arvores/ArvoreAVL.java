package Arvores;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ArvoreAVL extends Arvore {

    public ArvoreAVL() {
    }

    public ArvoreAVL(List<Node> listaNode) {
        this.listaNode = listaNode;
        if (!listaNode.isEmpty()) {
            raiz = listaNode.get(0);
        }
    }

    @Override
    public void put(int valor) {
        raiz = inserir(raiz, valor);
        
        atualizarPosicoes(raiz, 400, 120, distanciaX);

    }

    private Node inserir(Node atual, int valor) {
        if (atual == null) {
            Node novo = new Node(valor, EngineFrame.BLACK, 0, 0);
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

        int balanceamento = getBalanceamento(atual);

        if (balanceamento > 1 && valor < atual.getFilhoEsquerda().getValor()) {
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && valor > atual.getFilhoDireita().getValor()) {
            return rotacaoEsquerda(atual);
        }

        if (balanceamento > 1 && valor > atual.getFilhoEsquerda().getValor()) {
            atual.setFilhoEsquerda(rotacaoEsquerda(atual.getFilhoEsquerda()));
            return rotacaoDireita(atual);
        }

        if (balanceamento < -1 && valor < atual.getFilhoDireita().getValor()) {
            atual.setFilhoDireita(rotacaoDireita(atual.getFilhoDireita()));
            return rotacaoEsquerda(atual);
        }

        return atual;
    }

    private int getBalanceamento(Node n) {
        if (n == null) {
            return 0;
        }
        return getAlturaArvore(n.getFilhoEsquerda()) - getAlturaArvore(n.getFilhoDireita());
    }

    private Node rotacaoDireita(Node a) {
        Node b = a.getFilhoEsquerda();
        Node temp = b.getFilhoDireita();
        b.setFilhoDireita(a);
        a.setFilhoEsquerda(temp);
        
        atualizarPosicoes(raiz, 400, 120, distanciaX); 
        return b;
    }

    private Node rotacaoEsquerda(Node a) {
        Node b = a.getFilhoDireita();
        Node temp = b.getFilhoEsquerda();
        b.setFilhoEsquerda(a);
        a.setFilhoDireita(temp);
        

        atualizarPosicoes(raiz, 400, 120, distanciaX);
        return b;
    }

    @Override
    public void delete(int valor) {
        raiz = remover(raiz, valor);
        atualizarPosicoes(raiz, 400, 120, distanciaX);
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
            } else if (atual.getFilhoDireita() == null) {
                listaNode.remove(atual);
                return atual.getFilhoEsquerda();
            }

            Node sucessor = getMenor(atual.getFilhoDireita());
            atual.setValor(sucessor.getValor());
            
            atual.setFilhoDireita(remover(atual.getFilhoDireita(), sucessor.getValor()));
        }

        int fb = getBalanceamento(atual);

        if (fb > 1 && getBalanceamento(atual.getFilhoEsquerda()) >= 0) {
            return rotacaoDireita(atual);
        }

        if (fb > 1 && getBalanceamento(atual.getFilhoEsquerda()) < 0) {
            atual.setFilhoEsquerda(rotacaoEsquerda(atual.getFilhoEsquerda()));
            return rotacaoDireita(atual);
        }

        if (fb < -1 && getBalanceamento(atual.getFilhoDireita()) <= 0) {
            return rotacaoEsquerda(atual);
        }

        if (fb < -1 && getBalanceamento(atual.getFilhoDireita()) > 0) {
            atual.setFilhoDireita(rotacaoDireita(atual.getFilhoDireita()));
            return rotacaoEsquerda(atual);
        }

        return atual;
    }

    @Override
    public void transformacao1(List<Node> listaNode) {

        ArvoreBinariaBusca nova = new ArvoreBinariaBusca();
        for (Integer v : getValores()) {
            nova.put(v);
        }
        transformacao(new JanelaArvore("Árvore Binária de Busca", nova));
    }

    @Override
    public void transformacao2(List<Node> listaNode) {

        ArvoreVermelhaPreta nova = new ArvoreVermelhaPreta();
        for (Integer v : getValores()) {
            nova.put(v);
        }
        transformacao(new JanelaArvore("Árvore Vermelha e Preta", nova));
    }

    private void transformacao(EngineFrame e) {
        e.setIconImage(logo.buffImage);
        e.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}