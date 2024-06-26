package org.molgenis.asterix.model;

import java.util.*;

public class PgxGene {

    private String name;
    private Map<String, Snp> variants = new HashMap<>();
    private SortedMap<String, PgxHaplotype> pgxHaplotypes = new TreeMap<>();
    private PgxHaplotype wildType;
    private int chr;
    private int startPos = Integer.MAX_VALUE;
    private int endPos = 0;

    private Map<List<String>, String> functionToPredictedPhenotype = new HashMap<>();

    private String allele0;
    private String allele1;
    private String predictedPhenotype;

    public PgxGene(String name) {
        this.name = name;
        this.wildType = new PgxHaplotype(this, name + "*1");
    }

    public void getDeterminableAlleles() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedMap<String, PgxHaplotype> getPgxHaplotypes() {
        return pgxHaplotypes;
    }

    public PgxHaplotype getWildType() {
        return wildType;
    }

    public void setWildType(PgxHaplotype wildType) {
        this.wildType = wildType;
    }

    public void setPgxHaplotypes(SortedMap<String, PgxHaplotype> pgxHaplotypes) {
        this.pgxHaplotypes = pgxHaplotypes;
    }

    public String getAllele0() {
        return allele0;
    }

    public void setAllele0(String allele0) {
        this.allele0 = allele0;
    }

    public String getAllele1() {
        return allele1;
    }

    public void setAllele1(String allele1) {
        this.allele1 = allele1;
    }

    public Map<List<String>, String> getFunctionToPredictedPhenotype() {
        return functionToPredictedPhenotype;
    }

    public void setFunctionToPredictedPhenotype(Map<List<String>, String> functionToPredictedPhenotype) {
        this.functionToPredictedPhenotype = functionToPredictedPhenotype;
    }

    public String getPredictedPhenotype() {
        return predictedPhenotype;
    }

    public void setPredictedPhenotype(String predictedPhenotype) {
        this.predictedPhenotype = predictedPhenotype;
    }

    public int getChr() {
        return chr;
    }

    public void setChr(int chr) {
        this.chr = chr;
    }

    public int getStartPos() {
        return startPos;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    @Override
    public String toString() {
        return "PgxGene{" +
                "name='" + name + '\'' +
                ", pgxHaplotypes=" + pgxHaplotypes +
                ", functionToPredictedPhenotype=" + functionToPredictedPhenotype +
                ", allele0='" + allele0 + '\'' +
                ", allele1='" + allele1 + '\'' +
                ", predictedPhenotype='" + predictedPhenotype + '\'' +
                '}';
    }

    public void updateSnpInfo(Snp snp) {
        if (this.getVariants().containsKey(snp.getId())) {
            Snp haplotypeSnp = this.variants.get(snp.getId());
            haplotypeSnp.setAvailable(snp.isAvailable());
            haplotypeSnp.setMaf(snp.getMaf());
            haplotypeSnp.setrSquared(snp.getrSquared());
            haplotypeSnp.setAnnotations(snp.getAnnotations());
            haplotypeSnp.setHwe(snp.getHwe());
        }
    }

    public Map<String, Snp> getVariants() {
        return variants;
    }

    public List<String> getAnnotationFields() {
        Set<String> annotationFields = new HashSet<>();

        for (Snp variant : this.variants.values()) {
            annotationFields.addAll(variant.getAnnotations().keySet());
        }
        return new ArrayList<>(annotationFields);
    }

    public boolean hasVariant(Snp snp) {
        return variants.containsKey(snp.getId());
    }

    public void addVariant(Snp snp) {
        variants.put(snp.getId(), snp);
    }

    public void removeVariant(Snp pgxSnp) {
        this.variants.remove(pgxSnp.getId());
        // Remove SNP on all haplotypes and remove haplotype when empty
        for (Iterator<Map.Entry<String, PgxHaplotype>> it = pgxHaplotypes.entrySet().iterator(); it.hasNext(); ) {
            PgxHaplotype haplotype = it.next().getValue();
            haplotype.removeVariantAllele(pgxSnp);
            if (haplotype.getSnps().size() == 0) it.remove();
        }
    }
}
