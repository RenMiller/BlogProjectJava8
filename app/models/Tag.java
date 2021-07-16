package models;

import java.util.*;
import play.data.validation.*;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceOutput;
import org.hibernate.loader.custom.Return;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryImpl;
import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.MorphiaPlugin;

@Entity
public class Tag extends Model implements Comparable<Tag> {

    @Required
    public String name;

    private Tag(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }

    public int compareTo(Tag otherTag) {
        return name.compareTo(otherTag.name);
    }
    public static Tag findOrCreateByName(String name) {
        Tag tag = Tag.q("byName", name).first();
        if(tag == null) {
            tag = new Tag(name);
        }
        return tag;
    }

    public static Map<String, Integer> getCloud(){
        Map<String, Integer> hmap= new HashMap();

        MorphiaQuery morphiat = Tag.q();
        List<Tag> taglist = morphiat.asList();
        for (Tag itemtag: taglist){
            hmap.put(itemtag.name, 0);
        }

        MorphiaQuery morphiaq = Post.q();
        List<Post> lpost = morphiaq.asList();
        for (Post itempost: lpost) {
            for (String itemtag : itempost.tags) {
                Integer value = hmap.get(itemtag);
                if (value == null) {
                    value = 0;
                }
                value = value+1;
                hmap.put(itemtag, value);
            }
        }

        return hmap;

    }
}






