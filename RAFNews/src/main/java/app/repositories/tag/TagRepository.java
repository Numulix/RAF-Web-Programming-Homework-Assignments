package app.repositories.tag;

import app.entities.Tag;

import java.util.List;

public interface TagRepository {
    public Tag addTag(Tag tag);
    public List<Tag> allTags();
    public List<Integer> tagsForPost(Integer id);
}
