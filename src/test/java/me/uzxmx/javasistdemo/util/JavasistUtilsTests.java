package me.uzxmx.javasistdemo.util;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;

import javassist.bytecode.annotation.EnumMemberValue;
import me.uzxmx.javasistdemo.model.Base;
import me.uzxmx.javasistdemo.model.Book;
import me.uzxmx.javasistdemo.repository.BookRepository;
import me.uzxmx.javasistdemo.test.DirtiesContextBeforeAndAfterClassTestExecutionListener;
import me.uzxmx.javasistdemo.util.JavasistUtilsTests.Extension;

@ExtendWith(Extension.class)
@DataJpaTest
@TestExecutionListeners(
    listeners = DirtiesContextBeforeAndAfterClassTestExecutionListener.class,
    mergeMode = MergeMode.MERGE_WITH_DEFAULTS
)
    @DirtiesContext
public class JavasistUtilsTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testExceptionThrownWhenIdNotSpecified() {
        JpaSystemException exception = assertThrows(JpaSystemException.class, () -> {
            Book book = new Book();
            book.setName("Learn Spring");
            bookRepository.saveAndFlush(book);
        });

        assertTrue(exception.getMessage().startsWith("ids for this class must be manually assigned before calling save()"));
    }

    @Test
    void testOKWhenIdSpecified() {
        Book book = new Book();
        book.setId(1L);
        book.setName("Learn Spring");
        bookRepository.saveAndFlush(book);

        Book b = bookRepository.findById(1L).get();
        assertTrue(b.getName().equals("Learn Spring"));
    }

    public static class Extension implements BeforeAllCallback, AfterAllCallback {

        @Override
        public void beforeAll(ExtensionContext arg0) throws Exception {
            JavasistUtils.removeAnnotationFromField(Base.class, "id", GeneratedValue.class);
        }

        @Override
        public void afterAll(ExtensionContext arg0) throws Exception {
            JavasistUtils.addAnnotationToField(Base.class, "id", GeneratedValue.class, (annotation, constPool) -> {
                EnumMemberValue memberValue = new EnumMemberValue(constPool);
                memberValue.setType(GenerationType.class.getName());
                memberValue.setValue(GenerationType.IDENTITY.name());
                annotation.addMemberValue("strategy", memberValue);
            });
        }
    }
}
