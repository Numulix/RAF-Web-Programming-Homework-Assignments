package app.services;

import app.entities.Tag;
import app.repositories.tag.SqlTagRepository;
import app.repositories.tag.TagRepository;

import java.util.List;

public class TagService {

    private static final TagRepository tagRepository = new SqlTagRepository();

    public static List<Tag> allTags() {
        return tagRepository.allTags();
    }

    public static List<Integer> tagsForPost(Integer id) {
        return tagRepository.tagsForPost(id);
    }

}
