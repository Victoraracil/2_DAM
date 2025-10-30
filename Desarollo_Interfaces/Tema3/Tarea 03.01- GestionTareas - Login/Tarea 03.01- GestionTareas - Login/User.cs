using System.ComponentModel.DataAnnotations;

namespace Tarea_03._01__GestionTareas___Login
{
    public class User
    {
        [Key]
        public int Id { get; set; }
        [Required]
        [MaxLength(30)]
        public string Usuario { get; set; }
        [Required]
        public string PasswordHash { get; set; }
        [MaxLength(100)]
        public string NombreCompleto { get; set; }
        [Required]
        [MaxLength(100)]
        public string CorreoElectronico { get; set; }
        [Range(0, 1)]
        public int Activo { get; set; }
        public DateTime FechaCreacion { get; set; }
        public DateTime? FechaBaja { get; set; }
    }
}