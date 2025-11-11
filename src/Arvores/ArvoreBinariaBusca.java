package Arvores;

import br.com.davidbuzatto.jsge.core.engine.EngineFrame;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ArvoreBinariaBusca extends Arvore {

    public ArvoreBinariaBusca() {
    }

    public ArvoreBinariaBusca(List<Node> listaNode) {
        this.listaNode = listaNode;
        if (!listaNode.isEmpty()) {
            // A raiz é o primeiro elemento (índice 0)
            raiz = listaNode.get(0); 
        }
    }

    @Override
    public void put(int valor) {
        raiz = inserir(raiz, null, valor, 400, 120, distanciaX);

        atualizarPosicoes(raiz, 400, 120, distanciaX); 

    }

    private Node inserir(Node atual, Node pai, int valor, double x, double y, double desvioX) {

        if (atual == null) {
            Node novo = new Node(valor, EngineFrame.BLACK, x, y);
            listaNode.add(novo);

            if (pai != null) {
                if (valor < pai.getValor()) {
                    pai.setFilhoEsquerda(novo);
                } else if (valor > pai.getValor()) {
                    pai.setFilhoDireita(novo);
                } else {
                    return null;
                }
            }

            return novo;
        }

        if (valor < atual.getValor()) {
            atual.setFilhoEsquerda(inserir(
                    atual.getFilhoEsquerda(),
                    atual,
                    valor,
                    x - desvioX,
                    y + distanciaY,
                    desvioX / 1.75
            ));
        } else if (valor > atual.getValor()) {
            atual.setFilhoDireita(inserir(
                    atual.getFilhoDireita(),
                    atual,
                    valor,
                    x + desvioX,
                    y + distanciaY,
                    desvioX / 1.75
            ));
        }

        return atual;
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
            }
            if (atual.getFilhoDireita() == null) {
                listaNode.remove(atual);
                return atual.getFilhoEsquerda();
            }

            Node sucessor = getMenor(atual.getFilhoDireita());

            atual.setValor(sucessor.getValor());

            atual.setFilhoDireita(remover(atual.getFilhoDireita(), sucessor.getValor()));
        }

        return atual;
    }

    @Override
    public void transformacao1(List<Node> listaNode) {

        ArvoreAVL nova = new ArvoreAVL(); 
        for (Integer v : getValores()) {
            nova.put(v);
        }
        transformacao(new JanelaArvore("Árvore AVL", nova));
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