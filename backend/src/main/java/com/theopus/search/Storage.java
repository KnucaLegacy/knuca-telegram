package com.theopus.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.QueryBuilder;

import com.theopus.entitiy.schedule.Group;
import com.theopus.entitiy.schedule.Room;
import com.theopus.entitiy.schedule.Teacher;

public class Storage {

    private final Directory directory;
    private final IndexWriter writer;
    private final QueryParser queryParser;
    private IndexReader reader;
    private IndexSearcher searcher;

    public Storage() throws IOException {
        this.directory = new RAMDirectory();
        RussianAnalyzer analyzer = new RussianAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        this.writer = new IndexWriter(directory, conf);
        writer.commit();
        this.reader = DirectoryReader.open(directory);
        this.searcher = new IndexSearcher(reader);
        this.queryParser = new QueryParser("name", new RussianAnalyzer());
        queryParser.setAllowLeadingWildcard(true);

    }

    public void storeGroups(Collection<Group> groups) throws IOException {
        writer.addDocuments(groups.stream()
                .map(group -> create(group.getId(), group.getName(), SearchResultType.GROUP))
                .collect(Collectors.toList()));
        writer.commit();

        writer.flush();
    }

    public void storeTeachers(Collection<Teacher> teachers) throws IOException {
        writer.addDocuments(teachers.stream()
                .map(teacher -> create(teacher.getId(), teacher.getName(), SearchResultType.TEACHER))
                .collect(Collectors.toList()));
        writer.commit();
        writer.flush();
    }

    public void storeRooms(Collection<Room> rooms) throws IOException {
        writer.addDocuments(rooms.stream()
                .map(room -> create(room.getId(), room.getName(), SearchResultType.ROOM))
                .collect(Collectors.toList()));
        writer.commit();
        writer.flush();

    }

    public List<SearchResult> search(String text) throws ParseException, IOException {
        this.reader = DirectoryReader.open(directory);
        this.searcher = new IndexSearcher(reader);


        Query query = queryParser.parse(text);
        TopDocs search = searcher.search(query, 1000);
        return toResults(search);
    }

    public List<SearchResult> toResults(TopDocs search) throws IOException {
        List<SearchResult> result = new ArrayList<>();

        for (ScoreDoc scoreDoc : search.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            result.add(new SearchResult(Long.parseLong(doc.get("id")), doc.get("name"), SearchResultType.valueOf(doc.get("type"))));
        }

        return result;
    }

    private Document create(Long id, String name, SearchResultType type) {
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(id), Field.Store.YES));
        doc.add(new TextField("name", name, Field.Store.YES));
        doc.add(new StringField("type", type.name(), Field.Store.YES));
        return doc;
    }

    public enum SearchResultType {
        ROOM,
        TEACHER,
        GROUP
    }

    public static class SearchResult {
        public final Long id;
        public final String name;
        public final SearchResultType type;

        public SearchResult(Long id, String name, SearchResultType type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }

        @Override
        public String toString() {
            return "SearchResult{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
}
