var gulp = require('gulp');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var dest = require('gulp-dest');


gulp.task('compressjs', function () {
  gulp.src('../src/main/resources/org/got5/tapestry5/jquery/ui_1.12.1/*.js')
    .pipe(uglify())
    .pipe(rename({ suffix: '.min' }))
    .pipe(gulp.dest('js'));
})

gulp.task('compresswidgets', function () {
  gulp.src('../src/main/resources/org/got5/tapestry5/jquery/ui_1.12.1/widgets/*.js')
    .pipe(uglify())
    .pipe(rename({ suffix: '.min' }))
    .pipe(gulp.dest('js/widgets'));
})

gulp.task('compressi18n', function () {
  gulp.src('../src/main/resources/org/got5/tapestry5/jquery/ui_1.12.1/i18n/*.js')
    .pipe(uglify())
    .pipe(rename({ suffix: '.min' }))
    .pipe(gulp.dest('js/i18n'));
})

gulp.task('compresseffects', function () {
  gulp.src('../src/main/resources/org/got5/tapestry5/jquery/ui_1.12.1/effects/*.js')
    .pipe(uglify())
    .pipe(rename({ suffix: '.min' }))
    .pipe(gulp.dest('js/effects'));
})