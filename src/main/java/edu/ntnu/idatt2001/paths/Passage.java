package edu.ntnu.idatt2001.paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * The class represents a Passage.
 *
 * @author ...
 * @version JDK 17
 */
public class Passage {
    private final String title;
    private final String content;
    private final List<Link> links;


    public Passage(String title, String content) throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(title, "\nTitle cannot be null");
        Objects.requireNonNull(content, "\nContent cannot be null");
        if (title.isBlank()) throw new IllegalArgumentException("\nTitle cannot be blank");
        if (content.isBlank()) throw new IllegalArgumentException("\nContent cannot be blank");
        this.title = title;
        this.content = content;
        this.links = new ArrayList<>();
    }
    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public boolean addLink(Link link){
        return links.add(link);
    }

    public List<Link> getLinks(){
        return links;
    }

    public boolean hasLinks(){
        return !links.isEmpty();
    }
    @Override
    public String toString(){

        return "\nTitle: " + getTitle() + "\nContent: " + getContent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passage passage = (Passage) o;
        return getTitle().equals(passage.getTitle()) && getContent().equals(passage.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent());
    }


}
