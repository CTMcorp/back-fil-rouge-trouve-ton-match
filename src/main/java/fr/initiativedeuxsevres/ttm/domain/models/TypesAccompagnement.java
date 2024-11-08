package fr.initiativedeuxsevres.ttm.domain.models;

public enum TypesAccompagnement {
    RESSOURCES_HUMAINES("Ressources humaines"),
    FINANCE_COMPTABILITE("Finance et comptabilité"),
    JURIDIQUE("Juridique"),
    INFORMATIQUE("Informatique"),
    COMMERCIAL_COMMUNICATION("Commercial et communication");

    public final String name;
    TypesAccompagnement(String name) {
        this.name = name;
    }
}
